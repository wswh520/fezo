<%@ page language="java" pageEncoding="UTF-8"%>

	<input id="page_model_ipt_pageNo" type="hidden" value="$J{currentPage}" />
	<div class="page_navi" style="text-align:right;">
{@if totalPages>1}
		{@if currentPage>1}
				<a href="javascript:toPage($J{currentPage-1});" title="上一页">上一页</a>
		{@/if}
		{@if totalPages>8}
				<a href="javascript:toPage(1);" {@if currentPage==1}class="current"{@/if}>1</a>
			{@if totalPages-currentPage>4}
				{@if currentPage>4}
				<a href="javascript:void(0);">...</a>
				<a href="javascript:toPage($J{currentPage-2});">$J{currentPage-2}</a>
				<a href="javascript:toPage($J{currentPage-1});">$J{currentPage-1}</a>
				<a href="javascript:toPage($J{currentPage});" class="current">$J{currentPage}</a>
				<a href="javascript:toPage($J{currentPage-1+2});">$J{currentPage-1+2}</a>
				<a href="javascript:toPage($J{currentPage-1+3});">$J{currentPage-1+3}</a>
				{@else}
					{@each i in range(2, 7)}
				<a href="javascript:toPage($J{i});" {@if currentPage==i}class="current"{@/if}>$J{i}</a>
					{@/each}
				{@/if}
				<a href="javascript:void(0);">...</a>
			{@else}
				<a href="javascript:void(0);">...</a>
				{@each i in range(totalPages-5, totalPages)}
				<a href="javascript:toPage($J{i});" {@if currentPage==i}class="current"{@/if}>$J{i}</a>
				{@/each}
			{@/if}
				<a href="javascript:toPage($J{totalPages});" {@if currentPage==totalPages}class="current"{@/if}>$J{totalPages}</a>
		{@else if totalPages>0}
			{@each i in range(1, totalPages-1+2)}
				<a href="javascript:toPage($J{i});" {@if currentPage==i}class="current"{@/if}>$J{i}</a>
			{@/each}
		{@/if}
		{@if totalPages>currentPage}
				<a href="javascript:toPage($J{currentPage-1+2});" title="下一页">下一页</a>
		{@/if}
{@/if}
	</div>