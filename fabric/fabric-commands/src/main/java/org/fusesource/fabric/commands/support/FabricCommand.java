/**
 * Copyright (C) FuseSource, Inc.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.fabric.commands.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.fusesource.fabric.api.Container;
import org.fusesource.fabric.api.FabricService;
import org.fusesource.fabric.api.Profile;
import org.fusesource.fabric.service.FabricServiceImpl;
import org.fusesource.fabric.zookeeper.ZkClientFacade;
import org.linkedin.zookeeper.client.IZKClient;

public abstract class FabricCommand extends OsgiCommandSupport {

    private ZkClientFacade zooKeeper;
    protected FabricService fabricService;

    protected static String AGENT_PID = "org.fusesource.fabric.agent";
    protected static Long ZOOKEEPER_MAX_WAIT = 10000L;
    
    public FabricService getFabricService() {
        return fabricService;
    }

    public void setFabricService(FabricService fabricService) {
        this.fabricService = fabricService;
    }

    public ZkClientFacade getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZkClientFacade zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    protected void checkFabricAvailable() throws Exception {
       zooKeeper.checkConnected(ZOOKEEPER_MAX_WAIT);
    }

    protected String toString(Profile[] profiles) {
        if (profiles == null) {
            return "";
        }
        int iMax = profiles.length - 1;
        if (iMax == -1) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(profiles[i].getId());
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }

    protected Profile[] getProfiles(String version, List<String> names) {
        Profile[] allProfiles = fabricService.getVersion(version).getProfiles();
        List<Profile> profiles = new ArrayList<Profile>();
        if (names == null) {
            return new Profile[0];
        }
        for (String name : names) {
            Profile profile = null;
            for (Profile p : allProfiles) {
                if (name.equals(p.getId())) {
                    profile = p;
                    break;
                }
            }
            if (profile == null) {
                throw new IllegalArgumentException("Profile " + name + " not found.");
            }
            profiles.add(profile);
        }
        return profiles.toArray(new Profile[profiles.size()]);
    }

    /**
     * Gets the container by the given name
     *
     * @param name the name of the container
     * @return the found container, or <tt>null</tt> if not found
     */
    protected Container getContainer(String name) {
        Container[] containers = fabricService.getContainers();
        for (Container container : containers) {
            if (container.getId().equals(name)) {
                return container;
            }
        }
        return null;
    }

}
