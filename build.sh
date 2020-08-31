# command-line build for all Jareto artifacts (local alternative to Jenkins build)
mvn clean install --file jareto-parent/pom.xml -P gpg
mvn clean install --file jareto-common/pom.xml -P gpg
mvn clean install --file jareto-client/pom.xml -P gpg
mvn clean install --file jareto-server/pom.xml -P gpg