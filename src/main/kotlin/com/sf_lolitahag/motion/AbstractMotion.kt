/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import org.ehcache.Cache
import org.ehcache.config.builders.CacheManagerBuilder
import java.awt.Image

/**
 * パネル上で表示するオブジェクトのBaseクラス 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
abstract class AbstractMotion : IMotion {
    private val EMPTY = ""
    protected val CACHE_MANAGER = CacheManagerBuilder.newCacheManagerBuilder().build(true)!!

    override var axisX = 0
        protected set
    override var axisY = 0
        protected set
    override var axisShadowX = 0
        protected set
    override var axisShadowY = 0
        protected set
    override var isShow = true
        protected set
    protected var fileName = EMPTY
    protected var fileNameShadow = EMPTY
    protected lateinit var cache: Cache<String, Image>

    override val bodyImage: Image?
        get() = if (fileName == EMPTY) {
            null
        } else cache.get(fileName)

    override val shadowImage: Image?
        get() = if (fileNameShadow == EMPTY) {
            null
        } else cache.get(fileNameShadow)
}
