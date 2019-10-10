@echo off
set/p input=请输入加密字符串：
set/p password=请输入密钥：
echo 加密中...
java -cp %USERPROFILE%\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI algorithm=PBEWithMD5AndDES input=%input% password=%password%
pause