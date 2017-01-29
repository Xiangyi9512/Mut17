# The full pathname of the directory that contains Daikon
export DAIKONDIR=/cygdrive/c/Users/yxy/Documents/daikonparent/daikon
# The full Linux pathname of the directory that contains the Java JDK
export JAVA_HOME=/cygdrive/c/Users/yxy/Documents/JavaInstall
source $DAIKONDIR/scripts/daikon.bashrc
echo $CLASSPATH;
cd C:/Users/yxy/Documents/daikonparent;
cd C:Users/yxy/Documents/muScript;
var=0
for javaFile in $(/bin/ls src); 
do
	echo item: $javaFile;
	var=$((var=var+1))
	echo $var;
	session="session"
	sessionName=$session$var
	echo $sessionName;
	java mujava.cli.testnew $sessionName $javaFile
	java mujava.cli.genmutes -ALL $sessionName
	/bin/cp -r $sessionName "C:/Users/yxy/Documents/daikonparent/daikon/examples/program";
done
echo $PWD;
homeDirectory="C:/Users/yxy/Documents/daikonparent/daikon/examples/program";
cd $homeDirectory;
echo $PWD;
relativePath="";
for fullfile in $(/bin/ls -R);
do
	if [[ ${fullfile:0:1} == "." ]];
	then 
		relativePath=${fullfile#*/};
		relativePath=${relativePath%:*};
		echo storerelativePath: $relativePath;
	fi
	filename="${fullfile%.*}"
	extension="${fullfile#*.}"
	extensionString="java"
	if [ "$extension" == "$extensionString" ];
	then
		echo filename: $filename;
		echo extension: $extension;
		echo $PWD;
		echo relativePath: $relativePath;
		textFileName=${relativePath##*/};
		echo textFileName: $textFileName; 
		textFileExtension=".txt"
		textFile=$textFileName$textFileExtension;
		echo textFile: $textFile;
		cd $relativePath;
		echo $PWD;
		javac -g $fullfile;
		java daikon.Chicory --daikon $filename > $textFile & TASK_PID=$!
		echo $TASK_PID
		/bin/sleep 2
		taskkill /F /IM $TASK_PID
		taskkill /F /IM java.exe
		echo finished
		cd $homeDirectory;
	fi
done
cd C:/Users/yxy/Documents/PIT/handleInvariantFiles/src/handleInvariantFiles;
echo $PWD;
javac -g compare.java;
java compare;