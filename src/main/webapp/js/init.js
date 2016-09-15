$(function(){
	ctx = getHost();
	initLeftMenu();
	tabCloseEven();
	loginOut();
})
var ctx = '';
var _menus = {
	"menus": [
		{
			"menuid": "1", "icon": "icon-home", "menuname": "主页", "url": "home/home.html",
			"menus": []
		},
		{
			"menuid": "8", "icon": "icon-sys", "menuname": "施工组织管理",
			"menus": [
				{"menuid": "21", "menuname": "单元轨节管理", "icon": "icon-nav", "url": "http://baidu.com"},
				{"menuid": "22", "menuname": "施工计划管理", "icon": "icon-nav", "url": "http://lingyi.tech"}
			]
		}, {
			"menuid": "56", "icon": "icon-sys", "menuname": "查询管理",
			"menus": [{
				"menuid": "31",
				"menuname": "单元轨节查询",
				"icon": "icon-nav",
				"url": "statistics/danyuanguijie_query.html"
			},
				{
					"menuid": "32",
					"menuname": "单元轨节锁定查询",
					"icon": "icon-nav",
					"url": "statistics/danyuanguijies_locked_query.html"
				},
				{"menuid": "32", "menuname": "规范查询", "icon": "icon-nav", "url": "statistics/zuoyezhidaoshu_query.html"},
				{"menuid": "52", "menuname": "人员信息查询", "icon": "icon-nav", "url": "org/user.html"},
			]
		}, {
			"menuid": "28", "icon": "icon-sys", "menuname": "统计分析",
			"menus": [
				//{"menuid":"41","menuname":"单元轨节数量统计","icon":"icon-nav","url":"statistics/locked_release_query.html"},
				//{"menuid":"42","menuname":"锁定放散统计","icon":"icon-nav","url":"statistics/locked_release_statistics.html"},
				//{"menuid":"43","menuname":"用户统计","icon":"icon-nav","url":"bank.html"}
			]
		}, {
			"menuid": "39", "icon": "icon-sys", "menuname": "报表管理",
			"menus": [{"menuid": "51", "menuname": "放散锁定报表", "icon": "icon-nav", "url": "report/jibenjishu_report.html"}
			]
		}, {
			"menuid": "39", "icon": "icon-sys", "menuname": "日志查询",
			"menus": [
				{"menuid": "53", "menuname": "登陆频次", "icon": "icon-nav", "url": "logs/dlpc.html"},
				{"menuid": "53", "menuname": "操作频次", "icon": "icon-nav", "url": "logs/czpc.html"},
				{"menuid": "53", "menuname": "系统日志", "icon": "icon-nav", "url": "logs/xtrz.html"}
			]
		}, {
			"menuid": "39", "icon": "icon-sys", "menuname": "组织机构管理",
			"menus": [
				{"menuid": "51", "menuname": "单位管理", "icon": "icon-nav", "url": "org/org.html"},
				//   {"menuid":"53","menuname":"职务管理","icon":"icon-nav","url":"org/post.html"}
			]
		}, {
			"menuid": "39", "icon": "icon-sys", "menuname": "基础数据管理",
			"menus": [
				{"menuid": "52", "menuname": "区间管理", "icon": "icon-nav", "url": "basicdata/qujianguanli.html"},
				{"menuid": "51", "menuname": "线路管理", "icon": "icon-nav", "url": "basicdata/xianluguanli.html"},
				{"menuid": "52", "menuname": "线路授权", "icon": "icon-nav", "url": "basicdata/xianlushouquan.html"},
				{"menuid": "52", "menuname": "测量标准信息", "icon": "icon-nav", "url": "basicdata/celiangbiaozhun.html"}
			]
		},
		{
			"menuid": "39",
			"icon": "icon-sys",
			"menuname": "系统管理",
			"menus": [
				{"menuid": "51", "menuname": "角色管理", "icon": "icon-nav", "url": "admin/sys/role.html"},
				{"menuid": "52", "menuname": "菜单管理", "icon": "icon-nav", "url": "admin/sys/menu.html"},
				{"menuid": "53", "menuname": "参数管理", "icon": "icon-nav", "url": "admin/sys/param.html"}
			]
		}
	]
};
//初始化左侧
function initLeftMenu() {
		$("#nav").accordion({animate:false});
    $.each(_menus.menus, function(i, n) {
			var menulist = [];
			menulist.push('<ul>');
	    $.each(n.menus, function(j, o) {
				menulist.push('<li>');
				menulist.push('<div>');
				menulist.push('<a ref="');
				menulist.push(o.menuid);
				menulist.push('" href="#" rel="');
				menulist.push(o.url);
				menulist.push('" >');
				menulist.push('<span class="icon ');
				menulist.push(o.icon);
				menulist.push('" >.</span>');
				menulist.push('<span class="nav">');
				menulist.push(o.menuname);
				menulist.push('</span>');
				menulist.push('</a>');
				menulist.push('</div>');
				menulist.push('</li>');
	    });
			menulist.push('</ul>');
			$('#nav').accordion('add', {
				title: n.menuname,
				content: menulist.join(''),
				iconCls: 'icon ' + n.icon
			});

			//init home page
			if(n.icon === 'icon-home'){
				$('#tabs').tabs('add',{
					title:'主页',
					content:createFrame('home/home.html')
				});
			}

    });

	$('.easyui-accordion li a').click(function(){
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid);

		addTab(tabTitle,url,icon);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});

	//选中第一个
	var panels = $('#nav').accordion('panels');
	var t = panels[0].panel('options').title;
    $('#nav').accordion('select', t);
}
//获取左侧导航的图标
function getIcon(menuid){
	var icon = 'icon ';
	$.each(_menus.menus, function(i, n) {
		 $.each(n.menus, function(j, o) {
		 	if(o.menuid==menuid){
				icon += o.icon;
				return icon;
			}
		});
	});
	return icon;
}

//重新打开一个窗口，并关闭其他非主页窗口
function addTab(subtitle,url,icon){
	if(!$('#tabs').tabs('exists',subtitle)){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!='主页'){
				$('#tabs').tabs('close',t);
			}
		});
		$('#tabs').tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			icon:icon
		});

	}else{
		$('#tabs').tabs('select',subtitle);
		$('#mm-tabupdate').click();
	}
	tabClose();
}

function createFrame(url){
	return '<iframe scrolling="auto" frameborder="0" target="_top" src="'+url+'" style="width:100%;height:100%;"></iframe>';
}

function tabClose(){
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close',subtitle);
	})
	/*为选项卡绑定右键*/
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle =$(this).children(".tabs-closable").text();

		$('#mm').data("currtab",subtitle);
		$('#tabs').tabs('select',subtitle);
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven(){
	//刷新
	$('#mm-tabupdate').click(function(){
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update',{
			tab:currTab,
			options:{
				content:createFrame(url)
			}
		})
	})
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	})
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			msgShow('系统提示','后边没有啦~~','error');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			msgShow('系统提示','到头了，前边没有啦~~','error');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});

	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	})
}

//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

//login out
function loginOut(){
	$('#loginOut').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
					if (r) {
							location.href = '/logout';
					}
			});
	});
}

function getHost(){
	return window.location.host;
}