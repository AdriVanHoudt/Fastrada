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
7. When on the box make sure to run `sudo service nginx restart` if you can not access the webserver
8. Make sure to run post installation steps, example: symfony2 needs the composer to 	update the vendors, ...
9. Map `192.168.0.6` to `teamb.localhost.com` (Windows: C:\Windows\System32\drivers\etc\hosts, Ubuntu, mac: /etc/hosts)
9. Have fun using Vagrant :D

#### Used cookbooks from remote repositories:
	https://github.com/opscode-cookbooks/java (Stripped windows support)

## Misc
