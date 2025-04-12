package example;

import db.Entity;
import db.*;

import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content ;
    public static final int Document_ENTITY_CODE = 15;
    private Date creationDate ;
    private Date lastModificationDate;

    public Document (String content){
        this.content = content;

    }

    @Override
    public int getEntityCode() {
        return Document_ENTITY_CODE;
    }

    @Override
    public Document copy() {
        Document copyDocument = new Document(content);
        copyDocument.id = id;
        copyDocument.content = content;

        return copyDocument;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
