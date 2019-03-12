package tj.javadeveloper.issspyapp.commons.utils;

public enum CronExpressions {
    EVERY_MINUTE("0 0/1 * * * *"),
    EVERY_5_MINUTES("0 0/5 * * * *"),
    EVERY_15_MINUTES("0 0/15 * * * *"),
    EVERY_HOUR("0 0 0/1 * * *");


    private final String expression;

    CronExpressions(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }
}
