package com.king.hawkeye.filter;

/**
 * 日志等级 过滤器
 * Created by King on 16/3/10.
 */
public class LevelFilter extends LogFilter {

    private String level;

    private String[] chain;

    public LevelFilter(String level) throws Exception {
        this.level = level;
        this.chain = getWeighters(level);
        if (chain == null) {
            throw new Exception("wrong log level : " + level);
        }
    }

    @Override
    public String _filter(String content) {
        for (int i = 0; i < chain.length; i++) {
            if (content.toLowerCase().contains(chain[i])) {
                return content;
            }
        }
        return null;
    }

    private static final String[] fatalChain = new String[]{"fatal"};
    private static final String[] errorChain = new String[]{"fatal", "error"};
    private static final String[] warnChain = new String[]{"fatal", "error", "warn"};
    private static final String[] infoChain = new String[]{"fatal", "error", "warn", "info"};
    private static final String[] debugChain = new String[]{"fatal", "error", "warn", "info", "debug"};
    private static final String[] traceChain = new String[]{"fatal", "error", "warn", "info", "debug", "trace"};

    private static String[] getWeighters(String levelName) {
        switch (levelName.toLowerCase()) {
            case "fatal":
                return fatalChain;
            case "warn":
                return warnChain;
            case "info":
                return infoChain;
            case "debug":
                return debugChain;
            case "error":
                return errorChain;
            case "trace":
                return traceChain;
            default:
                return null;

        }
    }

}
