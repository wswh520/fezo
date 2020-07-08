<%@ page language="java" pageEncoding="UTF-8"%>

<script id="model_leftMenu" type="text/template">
{@each menus as it}
<div class="portal expanded">
	<h5 id="$J{it.menu}_menu_title" data-node="title" onclick="javascript:openMenu('$J{it.menu}');" style="cursor: pointer;">$J{it.name}</h5>
	<div id="$J{it.menu}_menu_display" data-node="display" class="body">
		<ul>
		{@each it.children as childIt}
			<li onclick="javascript:selectMenu('$J{it.menu}_menu_$J{childIt.menu}',$J{childIt.callback});" style="cursor: pointer;">
				<a id="$J{it.menu}_menu_$J{childIt.menu}" data-node="menu" href="javascript:void(0);">$J{childIt.name}</a>
			</li>
		{@/each}
		</ul>
	</div>
</div>
{@/each}
</script>