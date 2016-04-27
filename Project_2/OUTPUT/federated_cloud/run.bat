set classpath=lib/mysql-connector-java-5.1.6-bin.jar;lib/log.jar;lib/jsoup-1.7.3.jar;lib/apache.jar;lib/http.jar;lib/gnujaxp.jar;lib/jfreechart-1.0.13.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13-swt.jar;lib/swtgraphics2d.jar;lib/jcommon-1.0.16.jar;lib/iText-2.1.5.jar;lib/jwnl-1.3.3.jar;lib/miglayout-3.6-swing.jar;.;
javac -d . *.java
java iaas.FedCloudServer
pause