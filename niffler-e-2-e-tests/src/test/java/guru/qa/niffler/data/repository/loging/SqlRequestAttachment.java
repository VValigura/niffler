package guru.qa.niffler.data.repository.loging;

import io.qameta.allure.attachment.AttachmentData;

public class SqlRequestAttachment implements AttachmentData {

    private String name;
    private String sql;

    public SqlRequestAttachment(String name, String sql) {
        this.name = name;
        this.sql = sql;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getSql() {
        return sql;
    }

}
