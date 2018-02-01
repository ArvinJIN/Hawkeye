#! /bin/sh

#启动方法
start() {
    java -Xms128m -Xmx2048m -jar hawkeye-server.jar 5 > log.log &
    #tail -f logs/app.log
}

#停止方法
stop() {
ps -ef | grep hawkeye-server.jar | awk '{print $2}'
while read pid
do
kill -9 $pid
done
}

case "$1" in
start)
start;;
stop)
stop;;
restart)
stop
start;; *)
printf 'Usage: %s {start|stop|restart}\n'
"$prog"
exit 1;;
esac
