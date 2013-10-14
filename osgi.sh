#!/bin/sh

java -Dosgi.bundles=/Applications/eclipse/plugins/org.apache.felix.gogo.command_0.8.0.v201108120515.jar@start,/Applications/eclipse/plugins/org.apache.felix.gogo.runtime_0.8.0.v201108120515.jar@start,/Applications/eclipse/plugins/org.apache.felix.gogo.shell_0.8.0.v201110170705.jar@start -jar /Applications/eclipse/plugins/org.eclipse.osgi_3.8.2.v20130124-134944.jar -console
