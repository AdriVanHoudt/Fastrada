# Uitvoeren indien het niet werkt!

## Vagrant
cd ~ && git clone https://github.com/mosabua/maven-android-sdk-deployer.git && cd maven-android-sdk-deployer/ && mvn -pl com.simpligility.android.sdk-deployer:android-19 -am install

## Locaal
mvn install:install-file \
 -Dfile="C:\Users\Thomas\AppData\Local\Android\android-studio\sdk\platforms\android-19\android.jar" \
                    -DgroupId=com.google.android \
                    -DartifactId=android \
                    -Dversion=4.4.2 \
                    -Dpackaging=jar \
                    -DgeneratePom=true
