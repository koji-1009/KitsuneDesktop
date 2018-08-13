/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion

import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import java.awt.Image

abstract class AbstractMotion : IMotion {

    protected val cacheManager: CacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true)

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

    protected var fileName = ""
    protected var fileNameShadow = ""
    protected lateinit var cache: Cache<String, Image>

    override val bodyImage: Image? = cache.get(fileName)

    override val shadowImage: Image? = cache.get(fileNameShadow)
}
