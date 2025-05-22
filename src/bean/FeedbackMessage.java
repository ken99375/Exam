package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class FeedbackMessage implements Serializable {
    private int id;
    private String name;
    private String message;
    private Timestamp createdAt;

    public int getId()
    { return id; }
    public void setId(int id)
    { this.id = id; }

    public String getName()
    { return name; }
    public void setName(String name)
    { this.name = name; }

    public String getMessage()
    { return message; }
    public void setMessage(String message)
    { this.message = message; }

    public Timestamp getCreatedAt()
    { return createdAt; }
    public void setCreatedAt(Timestamp createdAt)
    { this.createdAt = createdAt; }
}
