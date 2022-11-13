package com.ukraine.dc.api;

public enum QueryTemplates {
   INSERT("INSERT INTO %s (%s) VALUES (%s);"),
   SELECT_ALL("SELECT %s FROM %s;"),
   SELECT_BY_ID("SELECT * FROM %s WHERE %s=%s;"),
   DELETE_BY_ID("DELETE FROM %s WHERE %s=%s;"),
   UPDATE_BY_ID("UPDATE %s SET %s WHERE %s=%s;");

   private final String query;

   QueryTemplates(String query) {
       this.query = query;
   }

   public String getQuery() {
       return query;
   }
}
