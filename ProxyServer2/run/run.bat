
cd %SOCCAR_HOME%\pt-ar-game-3\ProxyServer2\run

java -Djava.util.logging.config.file=conf/logging.properties -XX:+UseG1GC -jar %SOCCAR_HOME%/pt-ar-game-3/ProxyServer2/server/server-impl/target/server-impl-1.0-SNAPSHOT-jar-with-dependencies.jar

IF /I "%ERRORLEVEL%" NEQ "0" (
    ECHO execution failed
    pause
)