package portal;

import java.sql.Date;

public class Publication {
    private int id;
    private String title;
    private String type;
    private Date date;
    private String publisher;
    private String doi;
    private String documentPath;
    private int profId;
    private String professorName;
    private String abstractText; // New field

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }
    public String getDocumentPath() { return documentPath; }
    public void setDocumentPath(String documentPath) { this.documentPath = documentPath; }
    public int getProfId() { return profId; }
    public void setProfId(int profId) { this.profId = profId; }
    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }
    public String getAbstract() { return abstractText; }
    public void setAbstract(String abstractText) { this.abstractText = abstractText; }
}