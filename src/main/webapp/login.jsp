<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        var tailwind = {
            theme: {
                extend: {
                    colors: {
                        clifford: '#da373d'
                    }
                }
            }
        };
    </script>
    <style type="text/tailwindcss">
        @layer utilities {
            .content-auto {
                content-visibility: auto;
            }
        }
    </style>
    <script src="https://cdn.tailwindcss.com?plugins=forms,typography,aspect-ratio,line-clamp"></script>
</head>
<body class="bg-gray-100">
<div class="w-full h-screen flex justify-center items-center">
    <div class="grid grid-cols-1">
        <!-- Display error message -->
        <c:if test="${not empty requestScope.error}">
            <div class="text-red-500">${requestScope.error}</div>
        </c:if>
        <form action="/LoginServlet" method="post"
              class="w-[439px] h-full p-6 bg-white rounded-lg shadow flex-col justify-center items-center gap-6 inline-flex">
            <div class="flex-col justify-center items-center gap-3 flex">
                <div class="text-neutral-800 text-2xl font-bold font-'Roboto' leading-9">
                    Login to  your account
                </div>
                <div class="w-[372px] text-center text-slate-500 text-base font-bold font-'Roboto' leading-7">
                    Welcome! Enter your details to Login.
                </div>
            </div>

            <div class="self-stretch h-full flex-col justify-center items-start gap-3 flex">
                <!-- email input -->
                <div class="self-stretch h-[71px] justify-start items-start gap-3 inline-flex">
                    <div class="grow shrink basis-0 h-[42px] flex-col justify-start items-start gap-2 inline-flex">
                        <div class="self-stretch text-gray-800 text-sm font-bold font-'Roboto' leading-[21px]">
                            Your Email
                        </div>
                        <input class="self-stretch h-10 p-4 py-3 bg-white bg-opacity-0 rounded-lg border border-gray-300 justify-start items-center gap-2.5 inline-flex outline outline-0 focus:border-2 focus:border-purple focus:transition"
                               placeholder="name@example.com" type="email" name="email" />
                    </div>
                </div>
                <!-- password input -->
                <div class="self-stretch h-[71px] justify-start items-start gap-3 inline-flex">
                    <div class="grow shrink basis-0 h-[42px] flex-col justify-start items-start gap-2 inline-flex">
                        <div class="self-stretch text-gray-800 text-sm font-bold font-'Roboto' leading-[21px]">
                            Your Password
                        </div>
                        <input class="self-stretch h-10 p-4 py-3 bg-white bg-opacity-0 rounded-lg border border-gray-300 justify-start items-center gap-2.5 inline-flex outline outline-0 focus:border-2 focus:border-purple focus:transition"
                               placeholder="Enter your password" type="password" name="password" />
                    </div>
                </div>
            </div>
            <button type="submit" class="self-stretch px-[18px] py-2.5 bg-violet-800 rounded-lg shadow justify-center items-center gap-2 inline-flex hover:bg-violet-700 active:bg-violet-900">
                <div class="text-white text-sm font-bold font-'Roboto' uppercase leading-[21px]">
                    Login
                </div>
            </button>

            ${requestScope.errousername}
            <!-- Link to sign-up page -->
            <div class="text-neutral-800 text-sm font-'Roboto' leading-[21px]">
                Don't have an account? <a href="singup.jsp" class="text-clifford">Sign up</a>.
            </div>
        </form>
    </div>
</div>
</body>
</html>

