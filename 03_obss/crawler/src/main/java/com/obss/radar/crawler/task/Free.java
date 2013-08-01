/*
 * Copyright (c) 2006 Hyperic, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.obss.radar.crawler.task;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.cmd.SigarCommandBase;

/**
 * Display amount of free and used memory in the system.
 */
public class Free extends SigarCommandBase {

	@Override
	public void output(String[] arg0) throws SigarException {
		Mem mem   = this.sigar.getMem();
        System.out.println((float)mem.getUsed()/mem.getTotal()*100);
	}

	public static void main(String[] args) throws Exception {
        new Free().processCommand(args);
    }
}
