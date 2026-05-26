import api from "../services/api";

export const uploadImage = async (file) => {
  const formData = new FormData();
  formData.append("file", file);

  const res = await api.post("/s3/upload", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });

  return res.data; // S3 URL
};