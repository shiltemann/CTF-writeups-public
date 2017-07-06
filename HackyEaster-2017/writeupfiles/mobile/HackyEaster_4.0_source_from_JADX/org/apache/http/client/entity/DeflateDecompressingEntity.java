package org.apache.http.client.entity;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;

public class DeflateDecompressingEntity extends DecompressingEntity {

    /* renamed from: org.apache.http.client.entity.DeflateDecompressingEntity.1 */
    class C01391 implements InputStreamFactory {
        C01391() {
        }

        public InputStream create(InputStream instream) throws IOException {
            return new DeflateInputStream(instream);
        }
    }

    public DeflateDecompressingEntity(HttpEntity entity) {
        super(entity, new C01391());
    }
}
