package com.ytempest.wanandroid.activity.main;

import com.ytempest.wanandroid.base.block.BlockActivity;
import com.ytempest.wanandroid.base.block.IBlock;

import java.util.List;

public class MainActivity extends BlockActivity {

    @Override
    protected void onCreateBlocks(List<IBlock> blocks) {
        blocks.add(new MainViewBlock());
    }
}