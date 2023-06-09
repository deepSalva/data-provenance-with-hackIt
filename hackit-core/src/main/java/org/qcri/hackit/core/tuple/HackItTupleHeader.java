package org.qcri.hackit.core.tuple;

import org.qcri.hackit.core.action.HackItActionVector;
import org.qcri.hackit.core.tags.HackItTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class HackItTupleHeader<K> implements Serializable, HackItActionVector {
    private K id;
    protected int child = 0;
    private List<HackItTag> tags;
    private boolean has_Callback_tag = false;
    private boolean has_skip_tag = false;
    private boolean has_sendout_tag = false;
    private boolean has_haltjob_tag = false;

    public HackItTupleHeader() {
        this.id = generateID();
    }

    public HackItTupleHeader(K id) {
        this.id = id;
    }

    public HackItTupleHeader(K id, int child){
        this(id);
        this.child = child;
    }


    public K getId(){
        return this.id;
    }

    public void addTag(HackItTag tag){
        if(this.tags == null){
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
        updateActionVector(tag);
    }

    public void clearTags(){
        this.tags.clear();
        this.has_Callback_tag = false;
        this.has_haltjob_tag  = false;
        this.has_sendout_tag  = false;
        this.has_skip_tag     = false;
    }

    public Iterator<HackItTag> iterate(){
        if(this.tags == null){
            return Collections.emptyIterator();
        }
        return this.tags.iterator();
    }

    public abstract HackItTupleHeader<K> createChild();

    protected abstract K generateID();

    @Override
    public String toString() {
        return "HackItTupleHeader{" +
                "id=" + id +
                ", child=" + child +
                '}';
    }

    private void updateActionVector(HackItTag tag){
        this.has_Callback_tag = (tag.hasCallback())? true: this.has_Callback_tag;
        this.has_haltjob_tag  = (tag.isHaltJob())? true: this.has_haltjob_tag;
        this.has_sendout_tag  = (tag.isSendOut())? true: this.has_sendout_tag;
        this.has_skip_tag     = (tag.isSkip())? true: this.has_skip_tag;
    }

    @Override
    public boolean hasCallback() {
        return this.has_Callback_tag;
    }

    @Override
    public boolean isHaltJob() {
        return this.has_haltjob_tag;
    }

    @Override
    public boolean isSendOut() {
        return this.has_sendout_tag;
    }

    @Override
    public boolean isSkip() {
        return this.has_skip_tag;
    }
}
