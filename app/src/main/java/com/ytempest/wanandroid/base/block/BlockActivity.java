package com.ytempest.wanandroid.base.block;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;

import com.ytempest.wanandroid.base.BaseActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author heqidu
 * @since 2020/6/20
 */
public abstract class BlockActivity extends BaseActivity {

    private final List<IBlock> mBlocks = new LinkedList<>();
    private ArrayMap<Class<? extends IBlock>, Object> mBlockCaches;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateBlocks(mBlocks);
        for (IBlock block : mBlocks) {
            block.onAttach(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (IBlock block : mBlocks) {
            block.onSaveInstanceState(outState);
        }
    }

    protected abstract void onCreateBlocks(List<IBlock> blocks);

    @NonNull
    <T extends Block> T getBlock(Class<T> clazz) {
        if (mBlockCaches != null && mBlockCaches.containsKey(clazz)) {
            return (T) mBlockCaches.get(clazz);
        }
        for (IBlock block : mBlocks) {
            if (block.getClass() == clazz) {
                if (mBlockCaches == null) {
                    mBlockCaches = new ArrayMap<>();
                }
                mBlockCaches.put(clazz, block);
                return (T) block;
            }
        }
        throw new IllegalArgumentException("Block didn't exist, whether you had added this block: " + clazz.getCanonicalName());
    }
}
