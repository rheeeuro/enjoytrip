export function addMessage(message) {
  const div = document.createElement("div");

  div.className =
    "message fixed flex justify-center items-center bg-green-200 bottom-10 left-16 w-96 h-24 rounded-xl";
  div.innerHTML += `
    <path
      stroke-linecap="round"
      stroke-linejoin="round"
      d="M6 18L18 6M6 6l12 12"
    />
  </svg>
  ${message}`;
  document.body.appendChild(div);
}

export function deleteMessage() {
  const messages = document.querySelectorAll(".message");
  if (messages) {
    messages.forEach((m) => {
      m.style.display = "none";
    });
  }
}
