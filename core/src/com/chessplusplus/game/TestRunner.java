package com.chessplusplus.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class TestRunner
//        extends BlockJUnit4ClassRunner
        implements ApplicationListener {
//    private Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<FrameworkMethod, RunNotifier>();

//    public GdxTestRunner(Class<?> klass) throws InitializationError {
//        super(klass);
//        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
//
//        new HeadlessApplication(this, conf);
//        Gdx.gl = mock(GL20.class);
//    }

    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }

//    @Override
//    public void render() {
//        synchronized (invokeInRender) {
//            for (Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()) {
//                super.runChild(each.getKey(), each.getValue());
//            }
//            invokeInRender.clear();
//        }
//    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

//    @Override
//    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
//        synchronized (invokeInRender) {
//            // add for invoking in render phase, where gl context is available
//            invokeInRender.put(method, notifier);
//        }
//        // wait until that test was invoked
//        waitUntilInvokedInRenderMethod();
//    }

    /**
     *
     */
//    private void waitUntilInvokedInRenderMethod() {
//        try {
//            while (true) {
//                Thread.sleep(10);
//                synchronized (invokeInRender) {
//                    if (invokeInRender.isEmpty())
//                        break;
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
