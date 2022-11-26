#!/bin/bash

TOMCAT_PATH='/home/linux_xxxxx/tomcat8'
EXAM_PATH='/home/linux_xxxxx/Exam'

# get classpath
CP=${TOMCAT_PATH}/lib/servlet-api.jar

# 题目1
# 使用for循环，将webapps/ROOT/WEB-INF/lib下的所有jar包的文件名一一取出
# 并追加到变量CP中


# compile
javac -classpath $CP ${EXAM_PATH}/code/*.java -d ${EXAM_PATH}/target

if [ $? -ne 0 ]
then
   echo 'complie error'
   exit
fi

# 题目2
# 删除目录 webapps/ROOT/WEB-INF/classes 下的所有class文件
# 将target目录下的所有class文件 移动到 webapps/ROOT/WEB-INF/classes中



# 题目3
# 重启tomcat