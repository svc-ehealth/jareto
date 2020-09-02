# command-line build examples (local alternative to Jenkins build)

# local installation (without Sources / JavaDoc / GPG)
# mvn clean install

# local installation (with Sources / JavaDoc / GPG)
# mvn clean install -P release

# snapshot deployment to oss.sonatype.org (without Sources / JavaDoc / GPG)
# mvn clean deploy

# snapshot deployment to oss.sonatype.org (with Sources / JavaDoc / GPG)
# mvn clean deploy -P release

# release deployment WITHOUT Maven Release Plugin
###################################################

# remove "-SNAPSHOT" from version number
# mvn versions:set -DgenerateBackupPoms=false -DnewVersion=0.0.1

# staging deployment to oss.sonatype.org (with Sources / JavaDoc / GPG)
# this command is identical to the one used for snapshot deployment;
# Maven chooses the correct deployment type based on the version number 
# mvn clean deploy -P release

# after checking that the content of the staging repository is OK:
# trigger release of the staging repo
# (https://oss.sonatype.org/#view-repositories;staging~browsestorage~at/co/svc/jareto)
# mvn nexus-staging:release

# revert POM versions (no useless version bump commits)

# tag release version in Git (jareto-<VERSION>)

# release deployment WITH Maven Release Plugin
###################################################

# TODO
