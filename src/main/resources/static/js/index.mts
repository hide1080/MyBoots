type Category = {
    categoryId: string
    categoryName: string
}

type UserNotice = {
    userNoticeId: number
    accountId: number
    noticeId: number
    goodsId: number
    status: number
}

export function byId(id: string): HTMLElement {
    return document.getElementById(id) as HTMLElement
}

export function bySelector(selector: string): NodeListOf<Element> {
    return document.querySelectorAll(selector);
}

export function createElement(tagName: string): HTMLElement {
    return document.createElement(tagName) as HTMLElement
}

export function addEventListener(elm: HTMLElement, ev: string, h: (e: Event) => void): void {
    elm.addEventListener(ev, h)
}

export function categoryChange(): void {
    const topCategory = byId("topCategoryId") as HTMLOptionElement
    const subCategory = byId("categoryId") as HTMLOptionElement
    subCategory.innerHTML = ""
    fetch(`/api/category/${topCategory.value}`)
        .then(response => response.json())
        .then((categories: Category[]) => {
            console.log(`categories: ${categories}`)
            categories.forEach(category => {
                const opt = createElement("option") as HTMLOptionElement
                opt.value = category.categoryId
                opt.text = category.categoryName
                subCategory.appendChild(opt)
            })
            subCategory.classList.add("visible")
            subCategory.classList.remove("invisible")
        })
}

export function queryNotices() {
    const ds = byId('myboots-js').dataset
    const accountId = ds.accountId
    const myPage = ds.myPageUrl
    if (!myPage) {
        console.log("not logged in.")
        return
    } else {
        console.log("logged in.")
    }
    setInterval(() => {
        fetch(`/api/notice/${accountId}`)
            .then(response => response.json())
            .then((notices: UserNotice[]) => {

                if (notices.length === 0) {
                    return
                }

                const popup = createElement("div")
                popup.innerHTML = `<p>${notices.length}個の商品が売れました。<br />マイページをご確認ください。</p>`
                popup.classList.add("notice-popup")
                popup.classList.add("text-center")
                const button = createElement("button") as HTMLButtonElement
                button.textContent = "確認"
                addEventListener(button, "click", (e: Event) => {
                    popup.remove()
                })
                popup.append(button)
                document.getElementsByTagName("body")[0].append(popup)
                const left = Math.floor((window.innerWidth - popup.offsetWidth) / 2)
                const top  = Math.floor((window.innerHeight - popup.offsetHeight) / 2)
                popup.style.top = `${top}px`
                popup.style.left = `${left}px`
                popup.style.opacity = "1"

                const headerStr = `{
                    "${ds.csrfHeaderName}" : "${ds.csrfToken}",
                    "Content-Type": "application/json"
                }`
                const headers = JSON.parse(headerStr)

                notices.forEach(n => n.status = 1)
                const requestOptions: RequestInit = {
                    method: "PUT",
                    headers: headers,
                    body: JSON.stringify(notices)
                }

                fetch("/api/notice", requestOptions)
                    .then(response => console.log("noticed"))
            })
        }, 1000 * 10)
}
