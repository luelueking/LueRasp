package com.lue.rasp;

import com.lue.rasp.transform.AgentTransform;

import java.lang.instrument.Instrumentation;

public class RASPLauncher {

    /**
     * RASP初始化
     *
     * @param instrumentation inst
     */
    public void launch(Instrumentation instrumentation) throws Exception{
        initTransformer(instrumentation);
    }

    /**
     * 初始化 ClassFileTransformer
     *
     * @param inst inst
     */
    private void initTransformer(Instrumentation inst) {
        inst.addTransformer(new AgentTransform(inst));
    }

}