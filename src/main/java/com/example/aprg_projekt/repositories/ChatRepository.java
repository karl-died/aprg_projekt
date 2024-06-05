package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ChatRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void createChat(UUID accountId1, UUID accountId2) {
        String matched = """
            SELECT *
            FROM r_rating AS r1
            JOIN r_rating AS r2
            ON r2.accountId = r1.ratedId
            AND r2.ratedId = r1.accountId
            WHERE r1.accountId = ?
            AND r1.ratedId = ?
            AND r1.isLike
            AND r2.isLike;
        """;
        List<Map<String, Object>> res = jdbcTemplate.queryForList(matched, accountId1, accountId2);
        if(res.isEmpty()) {
           throw new IllegalStateException("Accounts are not matched");
        }

        String insert = "INSERT INTO chat (accountId1, accountId2) VALUES (?, ?)";
        jdbcTemplate.update(insert, accountId1, accountId2);
    }

    public void postMessage(String senderEmail, UUID recipientProfileId, String message, LocalDateTime dateSent) {
        UUID senderAccountId = jdbcTemplate.queryForObject("SELECT id FROM account WHERE email = ?", new Object[]{senderEmail}, new RowMapper<UUID>() {
            public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UUID.fromString(rs.getString("id"));
            }
        });
        UUID recipientAccountId = jdbcTemplate.queryForObject("SELECT accountId FROM profile WHERE id = ?", new Object[]{recipientProfileId}, new RowMapper<UUID>() {
            public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UUID.fromString(rs.getString("accountId"));
            }
        });

        String chatIdQuery = """
            SELECT id
            FROM chat
            WHERE (accountId1 = ? AND accountId2 = ?) OR (accountId1 = ? AND accountId2 = ?);
        """;
        UUID chatId;
        try {
            chatId = jdbcTemplate.queryForObject(chatIdQuery, new Object[]{senderAccountId, recipientAccountId, recipientAccountId, senderAccountId}, new RowMapper<UUID>() {
                public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return UUID.fromString(rs.getString("id"));
                }
            });
        } catch (Exception e) {
            this.createChat(senderAccountId, recipientAccountId);

            chatId = jdbcTemplate.queryForObject(chatIdQuery, new Object[]{senderAccountId, recipientAccountId, recipientAccountId, senderAccountId}, new RowMapper<UUID>() {
                public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return UUID.fromString(rs.getString("id"));
                }
            });
        }

        String insertMessageQuery = """
            INSERT INTO chatMessage (chatId, senderId, message, dateSent) VALUES
            (?, ?, ?, ?)
        """;

        jdbcTemplate.update(insertMessageQuery, chatId, senderAccountId, message, dateSent);
    }

    public List<ChatMessage> getChatMessages(String senderEmail, UUID recipientProfileId) {
        UUID senderAccountId = jdbcTemplate.queryForObject(
                "SELECT id FROM account WHERE email = ?",
                new Object[]{senderEmail},
                new RowMapper<UUID>() {
            public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UUID.fromString(rs.getString("id"));
            }
        });
        UUID recipientAccountId = jdbcTemplate.queryForObject(
                "SELECT accountId FROM profile WHERE id = ?",
                new Object[]{recipientProfileId},
                new RowMapper<UUID>() {
            public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UUID.fromString(rs.getString("accountId"));
            }
        });

        String chatIdQuery = """
            SELECT id
            FROM chat
            WHERE (accountId1 = ? AND accountId2 = ?) OR (accountId1 = ? AND accountId2 = ?);
        """;
        UUID chatId;
        try {
            chatId = jdbcTemplate.queryForObject(
                    chatIdQuery,
                    new Object[]{senderAccountId, recipientAccountId, recipientAccountId, senderAccountId},
                    new RowMapper<UUID>() {
                public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return UUID.fromString(rs.getString("id"));
                }
            });
        } catch (Exception e) {
            return new ArrayList<ChatMessage>();
        }

        String chatMessageQuery = """
                SELECT message, (senderId = ?) AS isIncoming, dateSent
                FROM chat JOIN chatMessage ON chat.id = chatMessage.chatId
                WHERE (chat.accountId1 = ? AND chat.accountId2 = ?)
                OR (chat.accountId1 = ? AND chat.accountId2 = ?)
                ORDER BY dateSent ASC;
        """;

        List<ChatMessage> chatMessages = jdbcTemplate.query(
                chatMessageQuery,
                new Object[] {recipientAccountId, senderAccountId, recipientAccountId, recipientAccountId, senderAccountId},
                new RowMapper<>() {
                    public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String[] dateString = rs.getString("dateSent").split("\\.");
                        LocalDateTime date;
                        date = LocalDateTime.parse(dateString[0], formatter);

                        return new ChatMessage(
                                rs.getString("message"),
                                rs.getBoolean("isIncoming"),
                                date
                        );
                    }
                });

        return chatMessages;
    }

}
