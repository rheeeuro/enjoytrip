import { localAxios } from "@/util/http-commons";

const local = localAxios();

function createBoard(board, success, fail) {
  local.defaults.headers["Authorization"] =
    "Bearer " + sessionStorage.getItem("accessToken");
  local.post(`/board/create`, JSON.stringify(board)).then(success).catch(fail);
}

function listBoard(param, success, fail) {
  local.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function getBoardById(boardId, success, fail) {
  local.get(`/board/${boardId}`).then(success).catch(fail);
}

function updateBoard(boardId, board, success, fail) {
  console.log(board);
  local.patch(`/board/${boardId}/update`, board).then(success).catch(fail);
}

function deleteBoard(boardId, success, fail) {
  local.patch(`/board/${boardId}/delete`).then(success).catch(fail);
}

function likeBoard(boardId, success, fail) {
  local.post(`/board/${boardId}/like`).then(success).catch(fail);
}

export {
  createBoard,
  listBoard,
  getBoardById,
  updateBoard,
  deleteBoard,
  likeBoard,
};
