@echo off
set/p input=����������ַ�����
set/p password=��������Կ��
echo ������...
java -cp %USERPROFILE%\.m2\repository\org\jasypt\jasypt\1.9.3\jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI algorithm=PBEWithMD5AndDES input=%input% password=%password%
pause