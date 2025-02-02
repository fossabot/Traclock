import { defineUserConfig } from 'vuepress'
import type { DefaultThemeOptions } from 'vuepress'

export default defineUserConfig<DefaultThemeOptions>({
    lang: 'zh-CN',
    title: '时迹',
    description: '时迹帮助文档',
    head: [
        ['link', { rel: 'icon', href: '/images/logo.svg' }],
        ['link', { rel: 'manifest', href: '/manifest.webmanifest' }],
        ['meta', { name: 'theme-color', content: '#3eaf7c' }],
        ['meta', { name: 'apple-mobile-web-app-capable', content: 'yes' }],
        ['meta', { name: 'apple-mobile-web-app-status-bar-style', content: 'black' }],
        ['link', { rel: 'apple-touch-icon', href: '/images/icons/apple-touch-icon-152x152.png' }],
        ['link', { rel: 'mask-icon', href: '/images/logo.svg', color: '#5bbad5' }],
        ['meta', { name: 'msapplication-TileColor', content: '#2d89ef' }]
    ],

    themeConfig: {
        logo: '/images/logo.png',
        repo: 'MeanZhang/Traclock',
        docsBranch: "main",
        docsDir: "docs",
        editLinkText: "编辑此页面",
        lastUpdatedText: "上次更新于",
        contributorsText:"贡献者"
    },

    // plugins: [
    //     [
    //         '@vuepress/pwa',
    //         {
    //             skipWaiting: true,
    //         },
    //     ],
    // ],
})