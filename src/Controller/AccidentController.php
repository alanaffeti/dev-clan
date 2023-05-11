<?php

namespace App\Controller;

use App\Entity\Accident;
use App\Entity\Categorie;
use App\Entity\Project;
use App\Form\AccidentType;
use App\Repository\AccidentRepository;
use App\Repository\ArticleRepository;
use App\Repository\ProjectRepository;
use App\Service\PictureService;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\String\Slugger\SluggerInterface;
use Swift_Message;
use Swift_Mailer;
#[Route('/accident')]
class AccidentController extends AbstractController
{
    #[Route('/', name: 'app_accident_index', methods: ['GET'])]
    public function index(AccidentRepository $accidentRepository,PaginatorInterface $paginator, Request $request): Response
    {
        $accident = $accidentRepository->findAll();

        $accident = $paginator->paginate(
            $accident, /* query NOT result */
            $request->query->getInt('page', 1), /*page number*/
            3 /*limit per page*/
        );


        return $this->render('accident/index.html.twig', ['accidents' => $accident]);
    }
    #[Route('/map',name:'map')]
    public function map(AccidentRepository $repository){

        return $this->render('accident/new.html.twig');

    }
    #[Route('/affiche', name: 'app_accident_index1', methods: ['GET'])]
    public function index2(AccidentRepository $accidentRepository): Response
    {
        return $this->render('base.html.twig', [
            'accidents' => $accidentRepository->findAll(),
        ]);
    }











    #[Route('/new', name: 'app_accident_new', methods: ['GET', 'POST'])]
    public function new(Request $request, AccidentRepository $accidentRepository,SluggerInterface $slugger,Swift_Mailer $mailer): Response
    {

        $accident = new Accident();
        $form = $this->createForm(AccidentType::class, $accident);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {


            //$imageFile = $form->get('image')->getData();



            $uploadedFiles = $form->get('image')->getData();
            $accident->setImage($this->movefiles($slugger,$uploadedFiles,$accident));


            $uploadedFiles = $form->get('image_voiture_reparee')->getData();
            $accident->setImageVoitureReparee($this->movefiles($slugger,$uploadedFiles,$accident));


            $uploadedFiles = $form->get('image_piece_endommage')->getData();
            $accident->setImagePieceEndommage($this->movefiles($slugger,$uploadedFiles,$accident));

            $uploadedFiles = $form->get('image_nouveau_pieces')->getData();
            $accident->setImageNouveauPieces($this->movefiles($slugger,$uploadedFiles,$accident));

            $uploadedFiles = $form->get('image_facture_reparation')->getData();
            $accident->setImageFactureReparation($this->movefiles($slugger,$uploadedFiles,$accident));





           /* if ($imageFile) {
                $originalfilename = pathinfo($accident->getNomClient(), flags: PATHINFO_FILENAME);
                $safefilename = $slugger->slug($originalfilename);
                $newFilename = $safefilename.'-'.uniqid() . '.' . $imageFile->guessExtension();

                try {
                    $imageFile->move(
                        $this->getParameter('personne_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // gestion de l'erreur
                }

                $accident->setImage($newFilename);

            }
*/






            $accidentRepository->save($accident, true);
            $this->sendEmail($mailer);

            return $this->redirectToRoute('client_front', [], Response::HTTP_SEE_OTHER);
        }
            return $this->renderForm('accident/new.html.twig', [
                'accident' => $accident,
                'form' => $form,
            ]);

    }


    #[Route("/allaccidentsjson", name:"list")]

    public function getaccidentsjson(AccidentRepository $repo, NormalizerInterface $normalizer)
    {

        $accidents = $repo->findAll();

        $accidentsNormalises = $normalizer->normalize($accidents,'json', ['groups' => "accident"]);

        $json = json_encode($accidentsNormalises);

        return new Response($json);
    }




   #[Route("/addaccidentjson/new", name:"list")]

    public function addaccidentjson(Request $req, NormalizerInterface $normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $accidents = new Accident();
        $accidents->setPrenomClient($req->get('prenom_client'));
        $accidents->setNomClient($req->get('nom_client'));
        $accidents->setMatricule($req->get('matricule'));
        $em->persist($accidents);
        $em->flush();

        $jsonContent = $normalizer->normalize($accidents,'json', ['groups' => 'accident']);

        return new Response(json_encode($jsonContent));

    }





    #[Route('/{id}', name: 'app_accident_show', methods: ['GET'])]
    public function show(Accident $accident): Response
    {
        return $this->render('accident/show.html.twig', [
            'accident' => $accident,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_accident_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Accident $accident, AccidentRepository $accidentRepository,SluggerInterface $slugger): Response
    {
        $form = $this->createForm(AccidentType::class, $accident);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {


            $uploadedFiles = $form->get('image')->getData();
            $accident->setImage($this->movefiles($slugger,$uploadedFiles,$accident));


            $uploadedFiles = $form->get('image_voiture_reparee')->getData();
            $accident->setImageVoitureReparee($this->movefiles($slugger,$uploadedFiles,$accident));



            $uploadedFiles = $form->get('image_piece_endommage')->getData();
            $accident->setImagePieceEndommage($this->movefiles($slugger,$uploadedFiles,$accident));



            $uploadedFiles = $form->get('image_nouveau_pieces')->getData();
            $accident->setImageNouveauPieces($this->movefiles($slugger,$uploadedFiles,$accident));

            $uploadedFiles = $form->get('image_facture_reparation')->getData();
            $accident->setImageFactureReparation($this->movefiles($slugger,$uploadedFiles,$accident));


            $accidentRepository->save($accident, true);

            return $this->redirectToRoute('app_accident_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('accident/edit.html.twig', [
            'accident' => $accident,
            'form' => $form,
        ]);
    }

    #[Route('/{id}/edit2', name: 'app_accident_edit2', methods: ['GET', 'POST'])]
    public function edit2($id,Request $request, Accident $accident, AccidentRepository $accidentRepository,SluggerInterface $slugger): Response
    {
        $form = $this->createForm(AccidentType::class, $accident);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {


            $uploadedFiles = $form->get('image')->getData();
            $accident->setImage($this->movefiles($slugger,$uploadedFiles,$accident));


            $uploadedFiles = $form->get('image_voiture_reparee')->getData();
            $accident->setImageVoitureReparee($this->movefiles($slugger,$uploadedFiles,$accident));



            $uploadedFiles = $form->get('image_piece_endommage')->getData();
            $accident->setImagePieceEndommage($this->movefiles($slugger,$uploadedFiles,$accident));



            $uploadedFiles = $form->get('image_nouveau_pieces')->getData();
            $accident->setImageNouveauPieces($this->movefiles($slugger,$uploadedFiles,$accident));

            $uploadedFiles = $form->get('image_facture_reparation')->getData();
            $accident->setImageFactureReparation($this->movefiles($slugger,$uploadedFiles,$accident));


            $accidentRepository->save($accident, true);

            return $this->redirectToRoute('app_accident_show', ['id'=>$id], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('accident/edit.html.twig', [
            'accident' => $accident,
            'form' => $form,
        ]);
    }






    #[Route('/{id}', name: 'app_accident_delete', methods: ['POST'])]
    public function delete(Request $request, Accident $accident, AccidentRepository $accidentRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$accident->getId(), $request->request->get('_token'))) {
            $accidentRepository->remove($accident, true);
        }

        return $this->redirectToRoute('app_accident_index', [], Response::HTTP_SEE_OTHER);
    }

//, methods: ['POST']
    #[Route('/{id}/{image}', name: 'app_image_delete', methods: ['GET'])]
    function deleteImage(int $image ,int $id, EntityManagerInterface $entityManager): Response
    {
        $accident = $entityManager->getRepository(Accident::class)->find($id);
        $imagePath = "/uploads/personne/";
        if ($accident){
            switch ($image){
                case 0:
                    $imagePath = $imagePath.$accident->getImage();

                    $accident->setImage(null);
                    break;
            }
            switch ($image){
                case 1:
                    $imagePath = $imagePath.$accident->getImageVoitureReparee();

                    $accident->setImageVoitureReparee(null);
                    break;
            }
            switch ($image){
                case 2:
                    $imagePath = $imagePath.$accident->getImagePieceEndommage();

                    $accident->setImagePieceEndommage(null);
                    break;
            }
            switch ($image){
                case 3:
                    $imagePath = $imagePath.$accident->getImageNouveauPieces();

                    $accident->setImageNouveauPieces(null);
                    break;
            }
            switch ($image){
                case 4:
                    $imagePath = $imagePath.$accident->getImageFactureReparation();

                    $accident->setImageFactureReparation(null);
                    break;
            }


            $entityManager->getRepository(Accident::class)->save($accident, true);
            if (file_exists($imagePath)) {
                unlink($imagePath);}
        }

      return $this->redirectToRoute('app_accident_show', ['id'=>$id], Response::HTTP_SEE_OTHER);
    }



   private  function movefiles(SluggerInterface $slugger,$uploadedFiles,Accident $accident ):string
   {
       $filePaths = [];

       foreach ($uploadedFiles as $file) {


           $originalfilename = pathinfo($accident->getNomClient(), flags: PATHINFO_FILENAME);
           $safefilename = $slugger->slug($originalfilename);
           $newFilename = $safefilename.'-'.uniqid() . '.' . $file->guessExtension();

           $file->move($this->getParameter('personne_directory'), $newFilename);
           $filePaths[] = $newFilename;





       }
       return implode(',',$filePaths);
   }

/*
    private  function movefiles1(SluggerInterface $slugger,$uploadedFiles,Accident $accident ):string
    {
        $filePaths = [];

        foreach ($uploadedFiles as $file) {


            $originalfilename = pathinfo($accident->getNomClient(), flags: PATHINFO_FILENAME);
            $safefilename = $slugger->slug($originalfilename);
            $newFilename = $safefilename.'-'.uniqid() . '.' . $file->guessExtension();
            $file->move($this->getParameter('personne1_directory'), $newFilename);
            $filePaths[] = $newFilename;

        }
        return implode(',',$filePaths);
    }
*/

    public function sendEmail( Swift_Mailer $mailer)
    {

        $message = (new Swift_Message('Hello Email'))
            ->setFrom('mohamedalaeddine.naffeti@esprit.tn')
            ->setTo('mohamedalaeddine.naffeti@esprit')
            ->setBody(
                $this->render(
                    'mailer/index.html.twig',

                ),
                'text/html'
            )
            ->addPart(
                $this->renderView(
                    'mailer/index.html.twig',

                ),
                'text/plain'
            );
        $mailer->send($message);

        return $this->render('mailer\index.html.twig');


    }





}
