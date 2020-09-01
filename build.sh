# command-line build examples (local alternative to Jenkins build)

# local installation (without Sources / JavaDoc / GPG)
# mvn clean install

# local installation (with Sources / JavaDoc / GPG)
# mvn clean install -P release

# snapshot deployment to oss.sonatype.org (without Sources / JavaDoc / GPG)
# mvn clean deploy

# snapshot deployment to oss.sonatype.org (without Sources / JavaDoc / GPG)
# mvn clean deploy -P release

# release deployment
# TODO
