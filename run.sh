echo "Compiling..."
javac -d out -sourcepath src src/com/test/threading/App.java
echo "Done!"
clear
echo "Running..."
java -cp out com.test.threading.App
