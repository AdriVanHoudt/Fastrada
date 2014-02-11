# Uitvoeren indien het niet werkt!

mvn install:install-file \
 -Dfile="C:\Users\Thomas\AppData\Local\Android\android-studio\sdk\platforms\android-19\android.jar" \
 -DgroupId=com.google.android \
 -DartifactId=android \
 -Dversion=4.4.2 \
 -Dpackaging=jar \
 -DgeneratePom=true