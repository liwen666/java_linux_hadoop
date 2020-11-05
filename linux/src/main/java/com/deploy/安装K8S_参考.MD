1 安装
如果您的系统是Mac，请提前安装 Homebrew 包管理工具及其Cask 扩展，Cask扩展安装方式：brew tap caskroom/cask。

#1.1 安装 Docker

#1.2 安装 VirtualBox  
VirtualBox 是一个虚拟机工具，可以让我们在一种系统中安装其他的操作系统，比如在Windows中安装Linux，或者Mac中安装Windows。

Ubuntu/Debian系统安装方式：

~ sudo apt-get install virtualbox
CentOS/Fedaro系统安装方式：

~ yum install VirtualBox

##cetos
导入epel安装源 
#yum  install epel-release
在 RHEL/CentOS 和 Fedora 上安装 VirtualBox 5.1

1.如果已经安装有老版本，需要先卸载原来版本

# yum remove VirtualBox*   yum的安装和删除
 2.添加VirtualBox安装源

 RHEL/CentOS 7/6/5
# cd /etc/yum.repos.d/
# wget http://download.virtualbox.org/virtualbox/rpm/rhel/virtualbox.repo 
RHEL/CentOS 5
# wget http://dl.fedoraproject.org/pub/epel/5/i386/epel-release-5-4.noarch.rpm
# rpm -Uvh epel-release-5-4.noarch.rpm
Fedora 24-12
# cd /etc/yum.repos.d/

# wget http://download.virtualbox.org/virtualbox/rpm/fedora/virtualbox.repo
3.安装相关依赖包
# yum update
# yum install binutils qt gcc make patch libgomp glibc-headers glibc-devel kernel-headers kernel-devel dkms
4.安装VirtualBox 5.2
# yum install VirtualBox-5.2
 5.重建VirtualBox 5.2内核模块
------------- Fedora 24-19 and CentOS/RHEL 7 -------------
＃/usr/lib/virtualbox/vboxdrv.sh setup
------------- Fedora 18-16 and CentOS/RHEL 6/5 -------------
#/etc/init.d/vboxdrv setup
## OR ##
#service vboxdrv setup
 6.添加用户到vboxusers中 （user_name替换为相应的用户名）
# usermod -a -G vboxusers user_name
7.如果有关于KERN_DIR的错误，则需要设置相应的全局变量
## RHEL / CentOS / Fedora ##
KERN_DIR=/usr/src/kernels/3.10.0-229.7.2.el7.x86_64
## Export KERN_DIR ##
export KERN_DIR
--------------------------------------------------------------------
#注意事项

----------------------------------------------------------------------
#1.3 安装 kubectl
kubectl 是一个用于管理 Kubernetes 的命令行工具。

Linux安装kubectl（使用如下其中一种方式）：

## 使用国内阿里云源安装
~ curl -LO http://kubernetes.oss-cn-hangzhou.aliyuncs.com/kubernetes-release/release/`curl -s http://kubernetes.oss-cn-hangzhou.aliyuncs.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl && chmod +x kubectl && sudo mv kubectl /usr/local/bin/kubectl

## 使用谷歌源安装（国内网路会很慢）
~ curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl && chmod +x kubectl && sudo mv kubectl /usr/local/bin/kubectl

##
~ kubectl version


#1.4 安装 Minikube
minikube 是一个本地Kubernetes集群的实现，支持MacOS、Linux和Windows系统。minikube的主要目标是，成为本地开发Kubernetes和支持Kubernetes全部特性的最佳工具。

Linux安装：

# 通过国内阿里云安装
 curl -Lo minikube http://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/releases/v1.2.0/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

# 通过谷歌源安装
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube && sudo install minikube /usr/local/bin


#命令
kubectl api-versions


minikube start                           # 启动minikube
minikube ip                              # 查看IP
minikube status                          # 查看状态
minikube stop                            # 关闭集群
minikube delete                          # 删除集群。如果启动出错，同时删除 ~/.minikube目录 再minikube start

kubectl config use-context minikube      # 使用minikube集群作为当前上下文
kubectl cluster-info         