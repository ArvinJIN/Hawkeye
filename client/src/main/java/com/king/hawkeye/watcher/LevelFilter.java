package com.king.hawkeye.watcher;

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
            throw new Exception("错误的日志类别。");
        }
    }

    @Override
    public String _filter(String content) {
        for (int i = 0; i < chain.length; i++) {
            if (content.toUpperCase().contains(chain[i])) {
                return content;
            }
        }
        return null;
    }

    private static final String[] fatalChain = new String[]{"FATAL"};
    private static final String[] errorChain = new String[]{"FATAL", "ERROR"};
    private static final String[] warnChain = new String[]{"FATAL", "ERROR", "WARN"};
    private static final String[] infoChain = new String[]{"FATAL", "ERROR", "WARN", "INFO"};
    private static final String[] debugChain = new String[]{"FATAL", "ERROR", "WARN", "INFO", "DEBUG"};
    private static final String[] traceChain = new String[]{"FATAL", "ERROR", "WARN", "INFO", "DEBUG", "TRACE"};

    private static String[] getWeighters(String levelName) {
        switch (levelName) {
            case "FATAL":
                return fatalChain;
            case "WARN":
                return warnChain;
            case "INFO":
                return infoChain;
            case "DEBUG":
                return debugChain;
            case "TRACE":
                return traceChain;
            default:
                return null;

        }
    }

}
