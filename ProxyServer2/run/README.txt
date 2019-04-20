Installing windows service:

prunsrv //IS//SoccarProxy --DisplayName="SoccarProxy" --Install="C:\Users\Kasutaja\Documents\pt-ar-game-3\ProxyServer2\run\prunsrv.exe" --Jvm=auto --StartMode=jvm --StopMode=jvm --StartClass=com.playtech.ptargame3.server.Starter --StartMethod start --StopClass=com.playtech.ptargame3.server.Starter  --StopMethod stop --Startup=auto --Classpath="C:\Users\Kasutaja\Documents\pt-ar-game-3\ProxyServer2\server\server-impl\target\server-impl-1.0-SNAPSHOT-jar-with-dependencies.jar" --JvmOptions=-Djava.util.logging.config.file=conf/logging.properties ++JvmOptions=-XX:+UseG1GC

Uninstalling windows service:
prunsrv //DS//SoccarProxy