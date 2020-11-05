# 1.  Centos7 系统 yum 安装源的替换
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup # 备份系统自带的安装源
curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo  # 更换为阿里云的安装源
sed -i -e '/mirrors.cloud.aliyuncs.com/d' -e '/mirrors.aliyuncs.com/d' /etc/yum.repos.d/CentOS-Base.repo #解决报错问题
yum makecache fast # 生成缓存
 # 2. Epel 镜像
yum install wget -y  #  安装下载工具
wget -O /etc/yum.repos.d/epel.repo http://mirrors.aliyun.com/repo/epel-7.repo  #下载安装源
yum makecache  # 生成缓存

 #Kubernetes 镜像
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

#4. Docker CE 镜像
yum install -y yum-utils device-mapper-persistent-data lvm2 # 安装工具和依赖
yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo   # 配置 yum 安装源
yum makecache fast  # 快速生成缓存

yum install docker-ce-18.09.7-3.el7 docker-ce-cli-18.09.7-3.el7 containerd.io

systemctl start docker



#安装单机 K8S

  ##  1. 安装 kubectl ， kubectl 是操作 k8s 集群的命令行工具
 yum install kubectl -y  #由于我们安装 kubernetes 安装源，所以直接安装就可以
 ##2.  下载 minikube 安装工具，这是一个快速在本地环境安装 k8s 的工具
curl -Lo minikube https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/releases/v1.12.0/minikube-linux-amd64
chmod +x minikube && sudo mv minikube /usr/local/bin/   # 赋予执行权限并加入环境变量

 minikube 在 Centos7系统上支持很多驱动， 这些驱动可以调用虚拟机程序创建 k8s 集群.
 kvm2     在 kvm 虚拟机中创建k8s ， 需要打开cpu 的嵌套虚拟化功能
 virtualbox  在 virtualbox 虚拟机中创建 k8s ， 需要打开cpu 的嵌套虚拟化功能
 docker  在 docker 容器中创建 k8s.
 none     使用本地docker 创建 k8s，类似于 kubeadm 单机版.
 
 ##1.  KVM2
 
         安装KVM虚拟机的软件
 yum install libvirt qemu-kvm -y # 安装虚拟机软件
 systemctl enable libvirtd --now   # 启动虚拟机并加入开机启动
 virt-host-validate  # 验证虚拟机有没有错误，
 
------------------------VMWARE 开启虚拟机的虚拟化--------------------------------
vi /etc/default/grub  # Edit the file /etc/default/grub and add intel_iommu=on to the existing GRUB_CMDLINE_LINUX line.
sudo grub2-mkconfig -o /boot/grub2/grub.cfg
reboot
modprobe fuse
virt-host-validate
 
       我的虚拟机展示一个 WARN, 一个 FAIL, 由于我有强迫症，修复了这两个错误，所以并没有测试不修复错误是否可以正常安装 k8s.
 
        1    WARN (IOMMU appears to be disabled in kernel. Add intel_iommu=on to kernel cmdline arguments)，修复方法 可以参考这里
         vi /etc/default/grub
         Edit the file /etc/default/grub and add intel_iommu=on to the existing GRUB_CMDLINE_LINUX line.
        Note: I have a lot going on here for mine, such as a fix for a weird usb power issue of my motherboard, enabling nested virtualization for kvm and more. Disregard those things and only add intel_iommu=on for this task!
        GRUB_TIMEOUT=5
        GRUB_DISTRIBUTOR="$(sed 's, release .*$,,g' /etc/system-release)"
        GRUB_DEFAULT=saved
        GRUB_DISABLE_SUBMENU=true
        GRUB_TERMINAL_OUTPUT="console"
        GRUB_CMDLINE_LINUX="nouveau.modeset=0 rd.driver.blacklist=nouveau nomodeset rhgb quiet xhci-hcd.quirks=262144 kvm-intel.nested=1 intel_iommu=on"
        GRUB_DISABLE_RECOVERY="true"
        
         Next, update grub2 via:
        sudo grub2-mkconfig -o /boot/grub2/grub.cfg
        reboot
        2 FAIL (Load the 'fuse' module to enable /proc/ overrides   修复方法：modprobe fuse，  如需实现开机加载， 可以参考这里，  抱歉，偷懒了，只是参考，还需自己改改.
 
      
 #  minikube 安装 k8s 
 #登录一台香港服务器，执行如下命令下载这个驱动文件
 curl -LO https://storage.googleapis.com/minikube/releases/latest/docker-machine-driver-kvm2
 #上传这个驱动文件到本地服务器后，执行如下命令
 chmod +x docker-machine-driver-kvm2 && sudo mv docker-machine-driver-kvm2 /usr/local/bin/
 minikube delete  
 minikube start --iso-url=https://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/iso/minikube-v1.7.3.iso --image-repository=registry.cn-hangzhou.aliyuncs.com/google_containers --registry-mirror=https://zgmke8qe.mirror.aliyuncs.com --driver=kvm2   --force




minikube start                           # 启动minikube
minikube ip                              # 查看IP
minikube status                          # 查看状态
minikube stop                            # 关闭集群
minikube delete                          # 删除集群。如果启动出错，同时删除 ~/.minikube目录 再minikube start
