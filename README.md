# Installation
## Installation Steps
### Staging
#### Mac Prerequisites
* Virtualbox (Get the latest version here: https://www.virtualbox.org/wiki/Downloads)
* Vagrant (Get the latest version here: http://downloads.vagrantup.com/)
* Git
* XCode
* XCode Command-Line Tools

#### Windows Prerequisites
* Virtualbox (Get the latest version here: https://www.virtualbox.org/wiki/Downloads)
* Vagrant (Get the latest version here: http://downloads.vagrantup.com/)
* Git
* Git Bash (Instead of CMD)

#### Installation
1. Clone this repository using `git clone https://github.com/AdriVanHoudt/Fastrada.git`
2. Open a terminal and navigate to the directory
3.	__(Windows only)__
	1. If you're on Windows, run `dos2unix install.sh` and `dos2unix chef.rb`
	2. Go to the VagrantFile and comment out the line that has `:nfs => true` on the end and uncomment the second line that doesn't have that.
4. Enter `vagrant plugin install vagrant-vbguest` (Upgrade guest additions)
4. Enter `vagrant up <sitename>` (example: `vagrant up teamb.localhost.com` for the development box)
5. Wait till it is completely done (Go grab some food or a drink :D)
6. When it is done press `vagrant ssh <sitename>` (example: `vagrant ssh teamb.localhost.com` for the vagrant box) to enter the terminal
7. Map `192.168.0.6` to `teamb.localhost.com` (Windows: C:\Windows\System32\drivers\etc\hosts, Ubuntu, mac: /etc/hosts)
8. Have fun using Vagrant :D

#### Used cookbooks from remote repositories:
	https://github.com/opscode-cookbooks/java (Stripped windows support)

## Misc
### Interesting Vagrant Commands
* `vagrant halt <sitename>` (Stops the vagrant box)
* `vagrant reload <sitename>` (Restarts the vagrant box)
* `vagrant provision <sitename>` (Runs the provisioner for vagrant again)

### Maven
* deploy: `mvn tomcat7:deploy`
* redeploy: `mvn tomcat7:redeploy` (Run after you ran the first deploy)
* Manager URL: `http://teamb.localhost.com:8080/manager`
* Credentials: `username`: `admin`, `password`: `admin`

## FAQ
### Vagrant
__(When booting i get a error saying that the guest machine entered an invalid state while waiting for it to boot.)__
This is a virtualbox error, make sure that you disable Enable `VT-x/AMD-V` in <machine> --> Settings --> System --> Acceleration
