package repo;

import model.User;
import util.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    private final Database db;

    public UserDao(Database db) {
        this.db = db;
    }

    public boolean register(int id, String username, String password) {
        String sql = "INSERT INTO users(id, username, password_hash) VALUES(?,?,?)";
        try (Connection c = db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, username.trim());
            ps.setString(3, HashUtil.sha256(password));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false; // likely duplicate id/username
        }
    }

    public User login(String username, String password) {
        String sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
        try (Connection c = db.connect(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            String storedHash = rs.getString("password_hash");
            String givenHash = HashUtil.sha256(password);

            if (!storedHash.equals(givenHash)) return null;
            return new User(rs.getInt("id"), rs.getString("username"));
        } catch (Exception e) {
            return null;
        }
    }
}
