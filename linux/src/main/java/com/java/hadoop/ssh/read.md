 cd ~/.ssh/
[hadoop@localhost .ssh]$ ssh-keygen -t rsa 
[hadoop@localhost .ssh]$ cat id_rsa.pub >> authorized_keys

[hadoop@localhost .ssh]$ chmod 600 ./authorized_keys