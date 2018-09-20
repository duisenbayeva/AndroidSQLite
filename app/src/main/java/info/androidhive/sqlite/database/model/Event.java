package info.androidhive.sqlite.database.model;


public class Event {
    public static final String TABLE_NAME = "events";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_FROM = "fromtime";
    public static final String COLUMN_TO = "totime";

    private int id;
    private String title;
    private String timestamp;
    private String from;
    private String to;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_FROM + " TEXT,"
                    + COLUMN_TO + " TEXT"
                    + ")";

    public Event() {
    }

    public Event(String title, String from, String to) {
        this.title = title;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
    }


    public Event(int id, String title, String timestamp, String from, String to) {
        this.id = id;
        this.title = title;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
