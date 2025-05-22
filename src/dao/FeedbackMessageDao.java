package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.FeedbackMessage;

public class FeedbackMessageDao extends Dao {
    public void save(FeedbackMessage msg) throws Exception {
        String sql = "INSERT INTO FEEDBACK_MESSAGE (NAME, MESSAGE) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, msg.getName());
            ps.setString(2, msg.getMessage());
            ps.executeUpdate();
        }
    }
}
