<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_userDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>系统管理</li>
			<li class="last">账号管理</li>
		</ul>
	</div>
	<div class="right">
		<select id="sel_year" onchange="javascript:toPage(1);" style="{@if userType!='PATRIARCH'}display:none;{@/if}float:left;height: 27px;margin-right: 5px;">
			<option value="$J{year}">$J{year}年</option>
		{@each  i in range(2015, year)}
			<option value="$J{i}">$J{i}年</option>
		{@/each}
			<option value="0">未填表</option>
		</select>
		<input style="font-size: 12px;" class="searchinp left" type="text" placeholder="账号/姓名" id="ipt_keyword"/>
		<button class="Btn" type="button" onclick="javascript:toPage(1);"><i class="icon icon-search"></i>搜索</button>
	</div>
	<div class="left">
		<button onclick="javascript:showAddUser();" class="Btn" type="button" name="roleChoose"><i class="icon icon-Add"></i>添加账号</button>
	</div>
	<div class="clear"></div>
	<input id="user_ipt_userType" value="$J{userType}" type="hidden" />
	<div id="div_display_list"></div>
</div>
</script>
<script id="model_userList" type="text/template">
	<div class="bs-docs-example">
		<table class="table table-bordered table-striped">
			<thead><tr>
				<th width="15%">姓名</th>
{@if userType=='GRADUATE_TEACHER'}
				<th width="20%">用户名</th>
				<th width="10%" style="text-align: center;">年份</th>
				<th width="10%" style="text-align: center;">班级</th>
				<th width="7%" style="text-align: center;">状态</th>
				<th width="38%">&nbsp;</th>
{@else if userType=='PATRIARCH'}
				<th width="20%">证件号</th>
				<th width="20%">注册时间</th>
				<th width="7%" style="text-align: center;">状态</th>
				<th width="38%">&nbsp;</th>
{@else}
				<th width="20%">用户名</th>
				<th width="7%" style="text-align: center;">状态</th>
				<th width="58%">&nbsp;</th>
{@/if}
			</tr></thead>
			<tbody>
	{@if totalRecords==0}
			<tr>
{@if userType=='GRADUATE_TEACHER'}
				<td colspan="6">暂无记录！</td>
{@else if userType=='PATRIARCH'}
				<td colspan="5">暂无记录！</td>
{@else}
				<td colspan="4">暂无记录！</td>
{@/if}
			</tr>
	{@else}
		{@each datas as user}
			<tr>
				<td id="user_name_$J{user.id}">$J{user.name}</td>
{@if userType=='PATRIARCH'}
				<td id="user_username_$J{user.id}">$J{user.usernameStr}</td>
				<td>$J{user.ctimeStr}</td>
{@else}
				<td id="user_username_$J{user.id}">$J{user.username}</td>
{@/if}
{@if userType=='GRADUATE_TEACHER'}
				<td id="user_year_$J{user.id}">$J{user.year}</td>
				<td id="user_bj_$J{user.id}">$J{user.bj}</td>
{@/if}			
				<td style="text-align: center;">
					{@if user.statusStr=='NORMAL'}正常{@else if user.statusStr=='FORBIDDEN'}已禁用{@else}未知{@/if}
				</td>
				<td>
					<div class="Btn btn-group" style="background:none; padding:0;">
						<button class="btn-small">操作</button>
						<button class="dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
						</button>
					</div>
					<div class="btn_group" style=" position:relative;">
					<ul class="dropdown-menu" style="display: none;">
						{@if user.statusStr=='NORMAL'}
                            {@if userType!='ADMIN' && userType!='RECRUIT_TEACHER' &&userType!='HEALTH_TEACHER' &&userType!='GRADUATE_TEACHER'}
							<li onclick="javascript:showModifyPassword('$J{user.id}');" style="cursor:pointer;"><a href="javascript:void(0);">重置密码</a></li>
                            {@/if}
							<li onclick="javascript:showEditUser('$J{user.id}');" style="cursor:pointer;"><a href="javascript:void(0);">修改</a></li>
							<li onclick="javascript:doForbidden('$J{user.id}');" style="cursor:pointer;"><a href="javascript:void(0);">禁用</a></li>
						{@else if user.statusStr=='FORBIDDEN'}
							<li onclick="javascript:doEnabled('$J{user.id}');" style="cursor:pointer;"><a href="javascript:void(0);">启用</a></li>
						{@/if}
							<li onclick="javascript:doDelUser('$J{user.id}');" style="cursor:pointer;"><a href="javascript:void(0);">删除</a></li>
					</ul>
					</div>
				</td>
			</tr>
		{@/each}
	{@/if}
			</tbody>
		</table>
	</div>
	<jsp:include flush="true" page="/WEB-INF/content/common/page_model.jsp" />
    <div class="left" style="height:36px; line-height:36px; "><span>共$J{totalRecords}条数据</span></div>
</script>
<script id="model_userEditInfo" type="text/template">
	<input id="user_ipt_id" value="$J{id}" type="hidden" />
	<div class="item">
		<label class="sele-label label-nick">姓名：</label><input id="user_ipt_name" value="$J{name}" type="text" class="inp" style="width: 174px;" /><label class="sele-label red">* 必填项</label>
	</div>
	<div class="item">
		<label class="sele-label label-nick">用户名：</label><input id="user_ipt_username" value="$J{username}" type="text" class="inp" style="width: 174px;" {@if userType=='PATRIARCH'}placeholder="请输入证件号"{@/if} /><label class="sele-label red">* 必填项</label>
	</div>
 {@if !isTeacher}
	<div data-node="password" class="item">
		<label class="sele-label label-nick">密码：</label><input id="user_ipt_password" type="password" class="inp" style="width: 174px;" /><label class="sele-label red">* 必填项</label>
	</div>
{@/if}
{@if userType=='GRADUATE_TEACHER'}
	<div class="item">
		<label class="sele-label label-nick">年份：</label><input id="user_ipt_year"  value="$J{year}" type="text" class="inp" style="width: 174px;" /><label class="sele-label red">* 必填项</label>
	</div>
	<div class="item">
		<label class="sele-label label-nick">班级：</label><input id="user_ipt_bj"  value="$J{bj}" type="text" class="inp" style="width: 174px;" /><label class="sele-label red">* 必填项</label>
	</div>
{@/if}
</script>