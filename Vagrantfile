# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  
  config.vm.box = 'ubuntu/vivid64'
	
  config.vm.provider "virtualbox" do |v|
   v.memory = 4096
   v.cpus = 2
  end  

  config.vm.network :forwarded_port, guest: 9000, host: 9000 # play application
  config.vm.network :forwarded_port, guest: 9200, host: 9200 # elasticsearch 
  config.vm.network :forwarded_port, guest: 9300, host: 9300 # elasticsearch 

  config.vm.provision :shell do |shell|
    shell.inline = "puppet module install elasticsearch-elasticsearch --force --modulepath '/vagrant/dev_env/puppet/modules'
    puppet module install puppetlabs-stdlib --force --modulepath '/vagrant/dev_env/puppet/modules'
    puppet module install puppetlabs-apt --force --modulepath '/vagrant/dev_env/puppet/modules'
    puppet module install puppetlabs-mysql --force --modulepath '/vagrant/dev_env/puppet/modules'
    "
  end

  config.vm.provision :puppet do |puppet| 
     puppet.module_path = "dev_env/puppet/modules" 
     puppet.manifests_path = "dev_env/manifests"
  end

end
