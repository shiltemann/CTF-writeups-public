package org.apache.http.client.methods;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.http.HttpRequest;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.concurrent.Cancellable;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.HttpParams;

public abstract class AbstractExecutionAwareRequest extends AbstractHttpMessage implements HttpExecutionAware, AbortableHttpRequest, Cloneable, HttpRequest {
    private final AtomicBoolean aborted;
    private final AtomicReference<Cancellable> cancellableRef;

    /* renamed from: org.apache.http.client.methods.AbstractExecutionAwareRequest.1 */
    class C01411 implements Cancellable {
        final /* synthetic */ ClientConnectionRequest val$connRequest;

        C01411(ClientConnectionRequest clientConnectionRequest) {
            this.val$connRequest = clientConnectionRequest;
        }

        public boolean cancel() {
            this.val$connRequest.abortRequest();
            return true;
        }
    }

    /* renamed from: org.apache.http.client.methods.AbstractExecutionAwareRequest.2 */
    class C01422 implements Cancellable {
        final /* synthetic */ ConnectionReleaseTrigger val$releaseTrigger;

        C01422(ConnectionReleaseTrigger connectionReleaseTrigger) {
            this.val$releaseTrigger = connectionReleaseTrigger;
        }

        public boolean cancel() {
            try {
                this.val$releaseTrigger.abortConnection();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

    protected AbstractExecutionAwareRequest() {
        this.aborted = new AtomicBoolean(false);
        this.cancellableRef = new AtomicReference(null);
    }

    @Deprecated
    public void setConnectionRequest(ClientConnectionRequest connRequest) {
        setCancellable(new C01411(connRequest));
    }

    @Deprecated
    public void setReleaseTrigger(ConnectionReleaseTrigger releaseTrigger) {
        setCancellable(new C01422(releaseTrigger));
    }

    public void abort() {
        if (this.aborted.compareAndSet(false, true)) {
            Cancellable cancellable = (Cancellable) this.cancellableRef.getAndSet(null);
            if (cancellable != null) {
                cancellable.cancel();
            }
        }
    }

    public boolean isAborted() {
        return this.aborted.get();
    }

    public void setCancellable(Cancellable cancellable) {
        if (!this.aborted.get()) {
            this.cancellableRef.set(cancellable);
        }
    }

    public Object clone() throws CloneNotSupportedException {
        AbstractExecutionAwareRequest clone = (AbstractExecutionAwareRequest) super.clone();
        clone.headergroup = (HeaderGroup) CloneUtils.cloneObject(this.headergroup);
        clone.params = (HttpParams) CloneUtils.cloneObject(this.params);
        return clone;
    }

    public void completed() {
        this.cancellableRef.set(null);
    }

    public void reset() {
        Cancellable cancellable = (Cancellable) this.cancellableRef.getAndSet(null);
        if (cancellable != null) {
            cancellable.cancel();
        }
        this.aborted.set(false);
    }
}
